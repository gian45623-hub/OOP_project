package rpgsumting;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class SoundEngine {
    private Sequencer sequencer;
    private boolean enabled;

    public void toggle() {
        if (enabled) {
            stop();
        } else {
            start();
        }
    }

    public void start() {
        try {
            if (sequencer == null) {
                sequencer = MidiSystem.getSequencer();
                sequencer.open();
                sequencer.setSequence(createTheme());
                sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            }
            sequencer.start();
            enabled = true;
        } catch (Exception exception) {
            enabled = false;
            System.err.println("Music could not start: " + exception.getMessage());
        }
    }

    public void stop() {
        if (sequencer != null) {
            sequencer.stop();
        }
        enabled = false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void close() {
        stop();
        if (sequencer != null) {
            sequencer.close();
        }
    }

    private Sequence createTheme() throws InvalidMidiDataException {
        Sequence sequence = new Sequence(Sequence.PPQ, 4);
        Track track = sequence.createTrack();

        addProgramChange(track, 0, 46, 0);
        addProgramChange(track, 1, 48, 0);

        int[] melody = { 69, 72, 74, 76, 74, 72, 67, 69, 71, 74, 72, 69, 64, 67, 69, 72 };
        int[] bass = { 45, 45, 52, 52, 43, 43, 50, 50 };

        for (int i = 0; i < melody.length; i++) {
            addNote(track, 0, melody[i], 72, i * 4, 4);
        }

        for (int i = 0; i < bass.length; i++) {
            addNote(track, 1, bass[i], 46, i * 8, 8);
        }

        return sequence;
    }

    private void addProgramChange(Track track, int channel, int instrument, long tick) throws InvalidMidiDataException {
        ShortMessage message = new ShortMessage();
        message.setMessage(ShortMessage.PROGRAM_CHANGE, channel, instrument, 0);
        track.add(new MidiEvent(message, tick));
    }

    private void addNote(Track track, int channel, int note, int velocity, long startTick, long duration)
            throws InvalidMidiDataException {
        ShortMessage on = new ShortMessage();
        on.setMessage(ShortMessage.NOTE_ON, channel, note, velocity);
        track.add(new MidiEvent(on, startTick));

        ShortMessage off = new ShortMessage();
        off.setMessage(ShortMessage.NOTE_OFF, channel, note, 0);
        track.add(new MidiEvent(off, startTick + duration));
    }
}

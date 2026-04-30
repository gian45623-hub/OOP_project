document.addEventListener('DOMContentLoaded', () => {

    // --- Lessons Tabs Logic ---
    const tabButtons = document.querySelectorAll('.tab-btn');
    const tabPanes = document.querySelectorAll('.tab-pane');

    if (tabButtons.length > 0) {
        tabButtons.forEach(btn => {
            btn.addEventListener('click', (e) => {
                const parentGroup = e.currentTarget.closest('.tabs-container') || document;
                const siblings = parentGroup.querySelectorAll('.tab-btn');
                const panes = parentGroup.querySelectorAll('.tab-pane');

                siblings.forEach(b => b.classList.remove('active'));
                panes.forEach(p => p.classList.add('hide'));

                const target = e.currentTarget.getAttribute('data-pane');
                e.currentTarget.classList.add('active');
                const targetEl = document.getElementById(target);
                if (targetEl) targetEl.classList.remove('hide');
            });
        });
    }

    // --- Assignments Drop Zone Logic ---
    const appState = { fileStaged: false };
    const dropZone = document.getElementById('drop-zone');
    const dropText = document.getElementById('drop-text');
    const submitBtn = document.getElementById('btn-submit-assignment');
    const feedbackText = document.getElementById('feedback-text');

    if (dropZone) {
        dropZone.addEventListener('click', () => {
            if (appState.fileStaged) return;

            dropZone.classList.replace('border-slate-300', 'border-emerald-500');
            dropZone.classList.replace('bg-transparent', 'bg-slate-200/50');
            dropText.innerHTML = '<i class="fa-regular fa-file-pdf mr-1"></i> <b>HCI_Midterm_Design.fig</b> staged for upload';

            submitBtn.removeAttribute('disabled');
            submitBtn.classList.replace('bg-slate-300', 'bg-emerald-600');
            submitBtn.classList.replace('text-slate-500', 'text-white');
            submitBtn.classList.replace('cursor-not-allowed', 'hover:bg-emerald-700');

            appState.fileStaged = true;
        });

        submitBtn.addEventListener('click', () => {
            if (!appState.fileStaged) return;

            submitBtn.innerHTML = "Submitting...";

            setTimeout(() => {
                submitBtn.innerHTML = "Assignment Submitted";
                submitBtn.classList.replace('bg-emerald-600', 'bg-green-600');
                submitBtn.classList.replace('hover:bg-emerald-700', 'hover:bg-green-700');
                submitBtn.setAttribute('disabled', 'true');

                dropZone.classList.add('opacity-50', 'pointer-events-none');
                feedbackText.classList.remove('opacity-0');

                const statusEl = document.getElementById('assignment-status');
                if (statusEl) {
                    statusEl.innerHTML = 'Status: Submitted';
                    statusEl.classList.remove('bg-slate-100/60', 'text-slate-800', 'border-slate-300');
                    statusEl.classList.add('bg-green-50', 'text-green-800', 'border-green-200');
                }
            }, 800);
        });
    }

    // --- Messages Chat Logic ---
    const chatInput = document.getElementById('chat-input');
    const sendBtn = document.getElementById('btn-send-msg');
    const chatWindow = document.getElementById('chat-window');

    if (sendBtn && chatInput && chatWindow) {
        function sendMessage() {
            const text = chatInput.value.trim();
            if (!text) return;

            const msgHTML = `
        <div class="self-end max-w-[80%] md:max-w-[60%] mt-4">
            <div class="bg-emerald-600 text-white p-3 rounded-2xl rounded-tr-none shadow-sm">
                ${text}
            </div>
            <span class="text-xs text-slate-400 mt-1 block text-right">Just now</span>
        </div>`;

            chatWindow.insertAdjacentHTML('beforeend', msgHTML);
            chatInput.value = '';
            chatWindow.scrollTop = chatWindow.scrollHeight;
        }

        sendBtn.addEventListener('click', sendMessage);
        chatInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') sendMessage();
        });
    }

});

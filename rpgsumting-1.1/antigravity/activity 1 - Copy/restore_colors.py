import os
import glob
import re

directory = r"c:\Users\gian4\OneDrive\Documents\antigravity\activity 1"

def restore_html(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()
    
    original = content

    # Replace any remnant background gradients with solid emerald-600
    content = re.sub(r'bg-gradient-to-[a-zA-Z]+\s+from-[a-zA-Z]+-\d+(?:/\d+)?\s+to-[a-zA-Z]+-\d+(?:/\d+)?', 'bg-emerald-600', content)
    
    # Replace any hover transition gradients with hover solid 
    content = re.sub(r'hover:from-[a-zA-Z]+-\d+\s+hover:to-[a-zA-Z]+-\d+', 'hover:bg-emerald-700', content)
    
    if content != original:
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"Purged gradients from {os.path.basename(file_path)}")
    else:
        print(f"No gradients found in {os.path.basename(file_path)}")

files = glob.glob(os.path.join(directory, '*.html')) + glob.glob(os.path.join(directory, '*.js'))

for f in files:
    restore_html(f)

import os
import glob
import re

directory = r"c:\Users\gian4\OneDrive\Documents\antigravity\activity 1"

def remove_gradients(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()

    original = content

    # Replace Tailwind button/bg gradients with solid emerald
    content = re.sub(r'bg-gradient-to-[a-zA-Z]+\s+from-[a-zA-Z]+-\d+(?:/\d+)?\s+to-[a-zA-Z]+-\d+(?:/\d+)?', 'bg-emerald-600', content)
    content = re.sub(r'hover:from-[a-zA-Z]+-\d+\s+hover:to-[a-zA-Z]+-\d+', 'hover:bg-emerald-700', content)
    
    # Specific edge cases in dashboard.html and others
    content = content.replace('bg-emerald-600 text-white p-4 rounded-2xl rounded-tr-sm shadow-md font-medium text-[15px]', 'bg-emerald-600 text-white p-4 rounded-2xl rounded-tr-sm shadow-md font-medium text-[15px]') # No change needed, just ensuring safety
    content = re.sub(r'bg-gradient-to-r from-emerald-500 to-teal-500', 'bg-emerald-500', content)
    content = re.sub(r'group-hover:from-emerald-400 group-hover:to-teal-400', 'group-hover:bg-emerald-400', content)
    
    # CSS replacements
    if file_path.endswith('.css'):
        content = content.replace(
            'background: linear-gradient(135deg, rgba(209, 250, 229, 0.7) 0%, rgba(15, 23, 42, 0.4) 100%);',
            'background: rgba(15, 23, 42, 0.15);'
        )
        content = content.replace(
            'background: linear-gradient(90deg, rgba(16, 185, 129, 0.15) 0%, transparent 100%);',
            'background: rgba(16, 185, 129, 0.1);'
        )
        content = content.replace(
            'background: linear-gradient(0deg, rgba(16, 185, 129, 0.05) 0%, transparent 100%);',
            'background: rgba(16, 185, 129, 0.05);'
        )
        
    if content != original:
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"Updated {os.path.basename(file_path)}")

files = glob.glob(os.path.join(directory, '*.html')) + \
        glob.glob(os.path.join(directory, '*.js')) + \
        glob.glob(os.path.join(directory, '*.css'))

for f in files:
    remove_gradients(f)

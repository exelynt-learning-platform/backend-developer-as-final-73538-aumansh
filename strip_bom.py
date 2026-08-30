import os
import glob

def remove_bom(filepath):
    with open(filepath, "rb") as f:
        content = f.read()
    if content.startswith(b"\xef\xbb\xbf"):
        with open(filepath, "wb") as f:
            f.write(content[3:])

for root, _, files in os.walk("src"):
    for file in files:
        if file.endswith(".java") or file.endswith(".properties"):
            remove_bom(os.path.join(root, file))


import io
import os
import re
import sys


def main(file):
    with io.open(os.path.join(sys.path[0], file), 'r', encoding='utf8') as file:
        string = file.readline().strip()
    regex = string.replace('_', '\w+')
    print(string)
    search_string(regex, len(string.split()))


def search_string(regex, len):
    with io.open(os.path.join(sys.path[0], 'Buch.txt'), 'r', encoding='utf8') as buch:
        pre = buch.readline().strip()
        i = 1
        for line in buch:
            if not line.strip():
                i += 1
                continue
            line = line.strip()
            check = pre + ' ' + ' '.join(line.split()[: len - 1])
            matches = re.findall(regex, check, re.IGNORECASE)
            if matches:
                for match in matches:
                    print(f'Line {i}: {match}')
            matches.clear()
            pre = line
            i += 1


if __name__ == "__main__":
    for i in range(6):
        print(f'// stoerung{i}.txt')
        main(f'stoerung{i}.txt')
        print('-----------------')

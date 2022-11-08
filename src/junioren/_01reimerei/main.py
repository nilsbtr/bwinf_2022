import os
import re
import sys


def main(filename):
    words = []
    with open(os.path.join(sys.path[0], filename), 'r', encoding='utf8') as file:
        for line in file:
            words.append(line.strip())

    while words:
        a = words[0]
        for b in words[1:]:
            if check_words(a, b):
                print(f'{a} + {b}')
        words.pop(0)


def check_words(a, b):
    if a.lower().endswith(b.lower()) or b.lower().endswith(a.lower()):
        return False

    sa = get_subvocals(a)
    sb = get_subvocals(b)

    if sa is None or sb is None:
        return False  # Keine Vokale gefunden

    if sa.lower() != sb.lower():
        return False  # Gleichheit ab maßgeblicher Vokalgruppe

    if len(sa) * 2 < len(a) or len(sb) * 2 < len(b):
        return False  # Ende enthält mind. 1/2 der Buchstaben

    return True


def get_subvocals(word):
    matches = re.finditer('[aeiouäöü]+', word, re.IGNORECASE)
    length = len(re.findall('[aeiouäöü]+', word, re.IGNORECASE))
    pointer = []  # Alle Start Positionen
    for match in matches:
        pointer.append(match.start())
    if length > 0:
        if length > 1:
            return word[pointer[-2] :]
        else:
            return word[pointer[0] :]
    else:
        return None


if __name__ == "__main__":
    for i in range(4):
        print(f'// reimerei{i}.txt')
        main(f'reimerei{i}.txt')
        print('-----------------')

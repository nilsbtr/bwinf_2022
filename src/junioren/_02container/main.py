import os
import sys


def main(filename):
    candidates = []
    excluded = []
    with open(os.path.join(sys.path[0], filename), 'r') as file:
        for line in file:
            line = list(map(int, line.split()))
            if line[0] not in excluded and line[0] not in candidates:
                candidates.append(line[0])
            if line[1] in candidates:
                candidates.remove(line[1])
            if line[1] not in excluded:
                excluded.append(line[1])
    if len(candidates) == 1:
        print(f'Der schwerste Container ist: Nr. {candidates[0]}')
    else:
        print(f'Nicht eindeutig bestimmbar: {candidates}')


if __name__ == "__main__":
    for i in range(5):
        print(f'// container{i}.txt')
        main(f'container{i}.txt')
        print('------------------')

import os
import sys
import numpy as np
import copy


class Task:
    def __init__(self, start, time):
        self.start = start
        self.time = time
        self.waited = 0

    def get_start(self):
        return self.start

    def get_time(self):
        return self.time

    def get_waited(self):
        return self.waited

    def progress(self):
        self.time -= 1
        return self.time == 0

    def wait(self):
        self.waited += 1


def main(filename):
    tasks = []
    with open(os.path.join(sys.path[0], filename), 'r', encoding='utf8') as file:
        for line in file:
            line = line.strip()
            if line.strip():
                tasks.append(Task(int(line.split()[0]), int(line.split()[1])))
    print('-- Simulation 1')
    simulation1(copy.deepcopy(tasks))
    print('-- Simulation 2')
    simulation2(copy.deepcopy(tasks))
    print('-- Simulation 3')
    simulation3(copy.deepcopy(tasks))


def simulation1(queue):
    wait = 0
    total = [0]

    for task in queue[:-1]:
        wait += task.get_time()
        total.append(wait)

    print('Average:', round(sum(total) / len(total)))
    print('Maximum:', wait)


def simulation2(queue):
    current = None
    wait = []
    done = []
    time = 0

    while wait or queue:
        time += 1
        if wait and current == None:
            new = find_lowtime(wait)
            current = wait.pop(new)
        while queue and queue[0].get_start() == time:
            if current != None:
                wait.append(current)
                wait.append(queue.pop(0))
                new = find_lowtime(wait)
                current = wait.pop(new)
            else:
                wait.append(queue.pop(0))
                new = find_lowtime(wait)
                current = wait.pop(new)
        for task in wait:
            task.wait()
        if current != None and current.progress():
            done.append(current)
            current = None
    print_times(done)


def simulation3(queue):
    current = None
    wait = []
    done = []
    time = 0

    while wait or queue:
        time += 1
        if wait and current == None:
            new = find_highwait(wait)
            current = wait.pop(new)
        if wait and time % 60 == 0:
            new = find_highwait(wait)
            current = wait.pop(new)
        while queue and queue[0].get_start() == time:
            wait.append(queue.pop(0))
        for task in wait:
            task.wait()
        if current != None and current.progress():
            done.append(current)
            current = None
    print_times(done)


def find_lowtime(wait):
    low = [0, wait[0].get_time()]
    for index, task in enumerate(wait):
        if task.get_time() < low[1]:
            low[0], low[1] = index, task.get_time()
    return low[0]


def find_highwait(wait):
    high = [0, wait[0].get_waited()]
    for index, task in enumerate(wait):
        if task.get_waited() > high[1]:
            high[0], high[1] = index, task.get_waited()
    return high[0]


def print_times(done):
    waited = list(filter(lambda a: a != 0, list(map(lambda t: t.get_waited(), done))))
    waited.sort(reverse=True)
    print(waited)
    print('Average:', round(np.average([task.get_waited() for task in done])))
    print('Maximum:', max(task.get_waited() for task in done))


if __name__ == "__main__":
    for i in range(5):
        print(f'// fahrradwerkstatt{i}.txt')
        main(f'fahrradwerkstatt{i}.txt')
        print('-------------------------')

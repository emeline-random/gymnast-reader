import subprocess
import sys

if __name__ == '__main__':
    subprocess.call(['java', '-jar',
                     'C:/Users/mimin/IdeaProjects/GymnastReader/release/GymnastReader.jar',
                     '-s', 'C:/Users/mimin/IdeaProjects/GymnastReader/release/GymnastReader.txt'] +
                    sys.argv[1:])

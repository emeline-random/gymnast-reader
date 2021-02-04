import subprocess
import sys

if __name__ == '__main__':
    subprocess.call(['java', '-jar',
                     'C:/path/to/jar/file/GymnastReader.jar',
                     '-s', 'C:/path/to/default/save/file/GymnastReader.txt'] +
                    sys.argv[1:])

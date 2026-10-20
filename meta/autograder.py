#!/usr/bin/env python3
#################################################################
# autograder.py
#################################################################

# std libs
import argparse
import datetime
import json
import os
import re
import subprocess

# Get project root (one level up from script location)
SCRIPT_DIR = os.path.dirname(os.path.abspath(__file__))
PROJECT_ROOT = os.path.abspath(os.path.join(SCRIPT_DIR, '..'))

# Load class configuration from meta/class.json
with open(os.path.join(SCRIPT_DIR, 'class.json')) as cfg:
    config = json.load(cfg)
ASSIGNMENTS = config['assignments']

verbose = False


def find_all_matches(pattern, string):
    pat = re.compile(pattern)
    pos = 0
    out = []
    while m := pat.search(string, pos):
        pos = m.end()
        out.append(m[0])
    return out


def exec_process(shell_cmd):
    if verbose:
        join = " ".join(shell_cmd)
        log(join)
    try:
        allowed_keys = ['PWD', 'PATH']
        clean_env = {k: os.environ[k] for k in allowed_keys if k in os.environ}
        result = subprocess.run(shell_cmd, text=True, capture_output=True, timeout=60, env=clean_env)
    except subprocess.TimeoutExpired:
        result = subprocess.CompletedProcess(shell_cmd, returncode=1, stdout="[ERROR] Maven timed out after 60 seconds. Check for infinite loops or hanging tests.\n", stderr="")
    except Exception as ex:
        result = ex
    if verbose:
        log(result.stdout)
    return result


def log(msg):
    print(str(datetime.datetime.now()) + ': ' + msg)


def maven_test(test_pattern, directory):
    maven_result = exec_process(
        ['mvn', 'test', '-B', '-f', directory, '--fail-at-end', '-Dtest=*' + test_pattern + '*',
         '-Dsurefire.failIfNoSpecifiedTests=false'])
    out = maven_result.stdout
    lines = out.split("\n")
    last_result = None
    error_lines = []
    for line in lines:
        if line.find("Tests run:") != -1:
            last_result = line
        if line.find("[ERROR] ") != -1:
            error_lines.append("    " + line[8:] + "\n")

    result = {"test": test_pattern, "run": 0, "grade": 0, "failed": 0, "errors": 0, "skipped": 0,
              "build_error": False}

    if last_result:
        nums = find_all_matches(r'[0-9]+', last_result)
        tests_run, tests_failed, tests_errors, tests_skipped = map(int, nums)
        raw = (tests_run - (tests_failed + tests_errors + tests_skipped)) / max(tests_run, 1)
        adjusted = (.5 + .5 * raw) * 100.0
        result["grade"] = float('{0:.2f}'.format(adjusted))
        result["run"] = tests_run
        result["failed"] = tests_failed
        result["errors"] = tests_errors
        result["skipped"] = tests_skipped
    else:
        result["build_error"] = True
        result["errors"] = 1

    result['error_lines'] = error_lines
    result['logs'] = out
    return result


def run(args):
    if args.assignment == "all":
        actual_assignments = [x for x in ASSIGNMENTS if x != 'all']
    else:
        actual_assignments = [args.assignment]

    for assignment in actual_assignments:
        result = maven_test(assignment, PROJECT_ROOT)
        if result["build_error"]:
            result_display = " (Build Error!)` "
        else:
            result_display = (" (run:" + str(result["run"]) + ", failed:" + str(result["failed"]) +
                              ", errors:" + str(result["errors"]) + ", skipped:" + str(result["skipped"]) + ")")
        print("* `" + assignment + ": " + str(result['grade']) + "%" + result_display + "`")
        if len(result["error_lines"]) != 0:
            print("  ```console")
            print("".join(result["error_lines"][:30]))
            if len(result["error_lines"]) > 30:
                print("  redacted see logs...")
            print("  ```\n")
        if args.logs:
            with open(assignment + ".log", "w") as f:
                f.write(result['logs'])

if __name__ == "__main__":

    parser = argparse.ArgumentParser("autograder", description="the autograder for this repository")

    commands = parser.add_subparsers(title="subcommands", dest="command", required=True)
    parser.add_argument("-v", default=False, action='store_true', dest="verbose", help='print verbose output')

    # run - run autograder on local repo
    parser_run = commands.add_parser("run", help='runs the autograder on the current repo')
    parser_run.add_argument("-a", "--assignment", dest='assignment', default="all", choices=ASSIGNMENTS,
                            help='assignment to grade')
    parser_run.add_argument("-l", "--logs", dest='logs', default=False, action='store_true',
                            help='Create logs for each test')

    parsed_args = parser.parse_args()
    verbose = parsed_args.verbose

    if parsed_args.command == "run":
        run(parsed_args)
    else:
        parser.print_help()

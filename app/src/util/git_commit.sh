#!/usr/bin/env bash

# git_commit.sh
# commit時に必要な処理を適時追加する.
# ローカル環境のVinskyルートディレクトリで実行すること.

echo "git add -A"
git add -A

echo "git commit"
git commit

echo "git push origin master"
git push origin master
# IA2025-TPI-1

Configure the upstream of your repository as follows:

```console
git remote add upstream git@github.com:detiuaveiro/IA2025-TPI-1-template.git
```

This allows you to more easily get updates on base code using the following commands:

```console
git fetch upstream
git checkout master
git merge --allow-unrelated-histories upstream/master
```

If merge reports conflicts, you need to edit the files and clean up the conflicts.



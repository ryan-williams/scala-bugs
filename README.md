## `AbstractMethodError` with conflicting versions of a `trait`

### Before

```bash
sbt +c/publishM2 +b/publishM2 +a/run
```

Each of Scala 2.11.11 and 2.12.3 print:

```bash
b
c
```

So far so good!

### After

- add a method `extra` to `C`
- publish a new C: `sbt +c/publishM2`
- add a call of `C.extra` in `A`
- run `A` again:
	- `sbt ++2.11.11 a/run` throws an `AbstractMethodError: a.A$.extra()V`
	- `sbt ++2.12.3 a/run` runs fine as before

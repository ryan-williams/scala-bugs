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

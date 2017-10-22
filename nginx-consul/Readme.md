
1. run consul:

```
consul agent -dev -ui
```

2. run consul-template for nginx:

```
consul-template -template="/path/nginx.tpl:/path/nginx.conf:nginx -s reload"
```

3. build app:

```
sbt assembly
```

4. run app:

```
java -jar target/scala-2.12/app.jar 10000 1
```



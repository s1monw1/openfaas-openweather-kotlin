## OpenFaaS Function for OpenWeather API

This function is based on a custom OpenFaaS [kotlin template](https://github.com/s1monw1/openfaas-kotlin), which you have to pull
explicitly using:

```
# Run in project directory
faas-cli template pull https://github.com/s1monw1/openfaas-kotlin.git
```

To build the function, you can run

```
faas-cli build -f kkon-openweather.yml
```

To then deploy it, run:

```
faas-cli deploy -f kkon-openweather.yml
```

### Call the function

After deployment, you will be able to execute it via [http://127.0.0.1:31112/function/kkon-openweather?city=Cologne](http://127.0.0.1:31112/function/kkon-openweather?city=Cologne)
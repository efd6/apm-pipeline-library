
Features
--------

pytest-otel plugin for reporting APM traces of tests executed.


Requirements
------------

* opentelemetry-api == 1.2
* opentelemetry-exporter-otlp == 1.2.0
* opentelemetry-sdk == 1.2.0


Installation
------------

You can install "pytest-otel" via `pip` or using the `setup.py` script.

```
git checkout https://github.com/elastic/apm-pipeline-library
cd apm-pipeline-library/resources/scripts
pip install ./pytest_otel
```

Usage
-----

`pytest_otel` is configured by adding some parameters to the pytest command line. Below are the descriptions:

* --endpoint: URL for the OpenTelemetry server. (Required). Env variable: `OTEL_EXPORTER_OTLP_ENDPOINT`
* --headers: Additional headers to send (i.e.: key1=value1,key2=value2). Env variable: `OTEL_EXPORTER_OTLP_HEADERS`
* --service-name: Name of the service. Env variable: `OTEL_SERVICE_NAME`
* --session-name: Name for the main span.
* --traceparent: Trace parent ID. Env variable: `TRACEPARENT`. See https://www.w3.org/TR/trace-context-1/#trace-context-http-headers-format
* --insecure: Disables TLS. Env variable: `OTEL_EXPORTER_OTLP_INSECURE`

```bash
cd pytest_otel
pytest --endpoint https://otelcollector.example.com:4317 \
       --headers "authorization=Bearer ASWDCcCRFfr" \
       --service-name pytest_otel \
       --session-name='My_Test_cases' \
       --traceparent=00-0af7651916cd43dd8448eb211c80319c-b7ad6b7169203331-01 \
       --insecure=False
```

**IMPORTANT**: If you use `--headers` the transaction metadata might expose those arguments
with their values. In order to avoid any credentials to be exposed, it's recommended to use the environment variables.
For instance, given the above example, a similar one with environment variables can be seen below:

```bash
OTEL_EXPORTER_OTLP_ENDPOINT=https://apm.example.com:8200 \
OTEL_EXPORTER_OTLP_HEADERS="authorization=Bearer ASWDCcCRFfr" \
OTEL_SERVICE_NAME=pytest_otel \
TRACEPARENT=00-0af7651916cd43dd8448eb211c80319c-b7ad6b7169203331-01 \
OTEL_EXPORTER_OTLP_INSECURE=False \
pytest --apm-session-name='My_Test_cases'
```

License
-------

Distributed under the terms of the `Apache License Version 2.0`_ license, "pytest-otel" is free and open source software

FROM rabbitmq:3-management
RUN mkdir /config
WORKDIR /config
ENV RABBITMQ_USER pczech
ENV RABBITMQ_PASSWORD pczech123
ENV RABBITMQ_PID_FILE /var/lib/rabbitmq/mnesia/rabbitmq
COPY init.sh .
RUN chmod 777 init.sh
EXPOSE 15672 5672
ENTRYPOINT ["/config/init.sh"]

FROM rabbitmq:3.6.4-management

# TBD : a way to import the current Erlang cookie of the rabbitmq server node?
RUN rabbitmqctl -n rabbit@rabbitmq-host add_user admin Sabbat2017
RUN rabbitmqctl -n rabbit@rabbitmq-host set_user_tags admin administrator
RUN rabbitmqctl -n rabbit@rabbitmq-host set_user_tags admin management
RUN rabbitmqctl -n rabbit@rabbitmq-host set_permissions -p / admin ".*" ".*" ".*"

RUN rabbitmqctl -n rabbit@rabbitmq-host change_password guest Sabbat2017

EXPOSE 15671 15672
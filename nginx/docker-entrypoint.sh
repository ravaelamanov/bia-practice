#!/usr/bin/env sh
set -eu

envsubst '${SERVICE_NAME} ${AUTH_TYPE} ${TARGET_SERVICE_HOST} ${TARGET_SERVICE_PORT} ${MIRROR_SERVICE_HOST} ${MIRROR_SERVICE_PORT}' < /etc/nginx/conf.d/default.conf.template > /etc/nginx/nginx.conf

exec "$@"
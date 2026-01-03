# based on https://dev.to/shrsv/supercharge-your-postgres-docker-setup-with-extensions-3leh

FROM postgres:16.9-bookworm

# Install build dependencies, pg_cron, and PostGIS
RUN apt-get update \
    && apt-get install -y --no-install-recommends \
        postgresql-server-dev-16 \
        postgresql-16-cron \
        postgresql-16-postgis-3 \
        build-essential \
        ca-certificates \
        git \
    && rm -rf /var/lib/apt/lists/*

# Add pg_cron to shared_preload_libraries
RUN echo "shared_preload_libraries = 'pg_cron'" >> /usr/share/postgresql/postgresql.conf.sample

# Create initialization script for pg_cron and PostGIS
RUN mkdir -p /docker-entrypoint-initdb.d
RUN echo '#!/bin/bash\nset -e\n\necho "Creating extensions..."\n\n# Set cron.database_name\npsql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" -c "ALTER SYSTEM SET cron.database_name TO \047$POSTGRES_DB\047;"\n\n# Reload config\npsql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" -c "SELECT pg_reload_conf();"\n\n# Create extensions\npsql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" -c "CREATE EXTENSION IF NOT EXISTS pg_cron;"\npsql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" -c "CREATE EXTENSION IF NOT EXISTS postgis;"\n\necho "Extensions installed and configured."' > /docker-entrypoint-initdb.d/10_extensions.sh
RUN chmod +x /docker-entrypoint-initdb.d/10_extensions.sh
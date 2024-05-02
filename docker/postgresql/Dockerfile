# Creates the minimum environment to package and run this Skyve application.
# Note that this uses an in-memory database and is only useful for demonstration/development.
FROM bizhubaustralia/skyve-wildfly-postgresql:latest

ENV LANG en_US.UTF-8

USER root
RUN mkdir /content
RUN chown jboss:jboss /content

RUN mkdir -p /opt/skyve/addins/
RUN chown jboss:jboss /opt/skyve/addins/

USER jboss
ADD skyve-content-8.3.0.zip /opt/skyve/addins/skyve-content-8.3.0.zip
ADD myOrders.war /opt/jboss/wildfly/standalone/deployments/myOrders.war
ADD myOrders.json /opt/jboss/wildfly/standalone/deployments
RUN touch /opt/jboss/wildfly/standalone/deployments/myOrders.war.dodeploy

ENV SKYVE_CONTENT='/content/' \
    SKYVE_DATASTORE_DIALECT='org.skyve.impl.persistence.hibernate.dialect.PostgreSQL95SpatialDialect' \
    DATABASE_URL='myOrders_db' \
    SKYVE_SECURITY_DOMAIN_NAME='myOrders' \
    SKYVE_JNDI_NAME='myOrdersDB'

ENTRYPOINT [ "entrypoint.sh" ]
CMD [ "/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-Djava.locale.providers=COMPAT,CLDR" ]

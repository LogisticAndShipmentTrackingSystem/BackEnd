import py_eureka_client.euka_client as eureka
from app.config.settings import settings

def register_eureka():
    eureka.init(
        eureka_server=settings.EUREKA_SERVER,
        app_name=settings.APP_NAME,
        instance_port=settings.SERVICE_PORT,
        instance_host=settings.HOST,
    )
from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker

from core.config import settings

# 创建数据库引擎
engine = create_engine(settings.DATABASE_URL)

# 创建会话
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

# 创建基类
Base = declarative_base()


# 依赖注入
def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

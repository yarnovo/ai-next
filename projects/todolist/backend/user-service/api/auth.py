from fastapi import APIRouter, Depends, HTTPException, status
from sqlalchemy.orm import Session

from core.database import get_db
from schemas.user import UserCreate, UserLogin, Token, TokenVerifyRequest, TokenVerifyResponse
from services.auth_service import AuthService

router = APIRouter()


@router.post("/register", response_model=Token, status_code=status.HTTP_201_CREATED)
async def register(user: UserCreate, db: Session = Depends(get_db)):
    """用户注册"""
    # 检查用户是否存在
    if AuthService.get_user_by_email(db, user.email):
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="Email already registered"
        )

    if AuthService.get_user_by_username(db, user.username):
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="Username already taken"
        )

    # 创建用户
    db_user = AuthService.create_user(db, user)

    # 生成 token
    access_token = AuthService.create_access_token(db_user.id)
    refresh_token = AuthService.create_refresh_token(db_user.id)

    return Token(
        access_token=access_token,
        refresh_token=refresh_token
    )


@router.post("/login", response_model=Token)
async def login(user_login: UserLogin, db: Session = Depends(get_db)):
    """用户登录"""
    # 验证用户
    user = AuthService.get_user_by_email(db, user_login.email)
    if not user or not AuthService.verify_password(user_login.password, user.hashed_password):
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Incorrect email or password"
        )

    if not user.is_active:
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="User account is inactive"
        )

    # 生成 token
    access_token = AuthService.create_access_token(user.id)
    refresh_token = AuthService.create_refresh_token(user.id)

    return Token(
        access_token=access_token,
        refresh_token=refresh_token
    )


@router.post("/verify", response_model=TokenVerifyResponse)
async def verify_token(request: TokenVerifyRequest, db: Session = Depends(get_db)):
    """验证 Token（供其他微服务调用）"""
    try:
        payload = AuthService.decode_token(request.token)
        user_id = payload.get("sub")

        if user_id is None:
            return TokenVerifyResponse(valid=False)

        # 获取用户信息
        user = AuthService.get_user_by_id(db, user_id)
        if not user or not user.is_active:
            return TokenVerifyResponse(valid=False)

        return TokenVerifyResponse(
            valid=True,
            user_id=user.id,
            email=user.email,
            username=user.username
        )
    except Exception:
        return TokenVerifyResponse(valid=False)


@router.post("/refresh", response_model=Token)
async def refresh_token(refresh_token: str):
    """刷新 Token"""
    # TODO: 实现刷新 Token 逻辑
    raise HTTPException(status_code=501, detail="Not implemented")

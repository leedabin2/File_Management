import React, { useState, useContext } from 'react'
import { useNavigate, useLocation } from 'react-router-dom'
import { useIsLogin } from '../../context/IsLoginContext';
import { BsCloudArrowDown } from 'react-icons/bs'

const LoginBtn = () => {

    const navigate = useNavigate();
    return (
        <button className='bg-[#F7F6FB] w-[120px] h-[40px] rounded-[16px]' onClick={() => navigate("/login")}>로그인</button>
    )
}

const LogoutBtn = () => {

    const { isLogin, setIsLogin } = useIsLogin();

    const logout = () => {
        window.localStorage.removeItem('refreshToken');
        setIsLogin(false);
    }

    return (
        <button className='bg-[#F7F6FB] w-[120px] h-[40px] rounded-[16px]' onClick={logout}>로그아웃</button>
    )
}


function Navbar() {

    const navigate = useNavigate();
    const location = useLocation();

    const {isLogin, setIsLogin} = useIsLogin();
    return (
        <div className='relative z-30 w-full h-[80px] bg-white flex items-center'>
            <button onClick={() => navigate('/')}><div className={`ml-[16px] w-[48px] h-[48px] bg-[#F7F6FB] rounded-[14px] flex justify-center items-center text-2xl shadow-customIconBox text-${location.pathname.includes('file') ? '[#999999]' : '[#6367EB]'}`}><HiMiniHome/></div></button>
            <input className='absolute left-[80px] bg-[#F7F6FB] text-center w-[200px] h-[40px] rounded-[16px]' placeholder='코드 입력'></input>
            <div onClick={() => navigate('/')} className='hover:cursor-pointer absolute left-1/2 -translate-x-1/2 text-center bg-[#F7F6FB] w-[160px] h-[40px] flex items-center justify-center'><BsFillCloudArrowUpFill className='text-black' /><p>ICloduU</p></div>
            <div className='absolute right-10'>{isLogin ? <LogoutBtn /> : <LoginBtn />}</div>
        </div>
    )
}

export default Navbar

// import Navbar from '../components/bar/Navbar';
// import Sidebar from '../components/bar/Sidebar';
// import { Outlet } from 'react-router-dom';

// // const UpperLeftBlock = () => {

// //     return (
// //         <div className='w-48 h-48 bg-white -z-50'>
// //             <div className=' relative left-24 top-24 w-36 h-36 rounded-full bg-black'></div>
// //         </div>
// //     )
// // }

// const MainLayout = () => {
//     return (
//         <>
//             <div className='relative z-[100]'>
//                 <Sidebar></Sidebar>
//             </div>
//             <div className='h-screen'>
//                 <div className='relative z-[200]'>
//                     <Navbar></Navbar>
//                 </div>
//                 {/* <div className='page-area bg-[#F7F6FB]'/> */}
//                 <div className='w-full h-10 ml-[80px] fixed left-0 top-[80px] z-[200] bg-white'/>
//                 <div className='w-full h-10 ml-[80px] fixed left-0 top-[80px] z-[300] bg-[#F7F6FB] rounded-tl-[30px]'/>
//                 <div className='top-[100px] left-[80px] z-[500] bg-[#E7F6FB] relative'>
//                     <Outlet></Outlet>
//                 </div>
//             </div>
//         </>
//     )
// }

// export default MainLayout


import Navbar from '../components/bar/Navbar';
import Sidebar from '../components/bar/Sidebar';
import { Outlet } from 'react-router-dom';

// const UpperLeftBlock = () => {

//     return (
//         <div className='w-48 h-48 bg-white -z-50'>
//             <div className=' relative left-24 top-24 w-36 h-36 rounded-full bg-black'></div>
//         </div>
//     )
// }

const MainLayout = () => {
    return (
        <> 
            <Sidebar></Sidebar>
            <div className='h-screen'>
                <Navbar></Navbar>
                {/* <div className='page-area rounded-tl-3xl p-10 bg-[#F7F6FB] h-full'> */}
                <div className='page-area bg-[#F7F6FB] relative min-h-screen'>
                <div className='w-full h-10 ml-[80px] fixed left-0 top-[80px] z-[200] bg-white'></div>
                <div className='w-full h-10 ml-[80px] fixed left-0 top-[80px] z-[300] bg-[#F7F6FB] rounded-tl-[30px]'></div>
                    <div className='pt-[120px]'>
                        <Outlet></Outlet>
                    </div>
                </div>
            </div>
        </>
    )
}

export default MainLayout
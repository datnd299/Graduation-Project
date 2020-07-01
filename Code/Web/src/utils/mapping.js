const RoleMap={
    'admin':{
        text:'Admin hệ thống',
        color:'teal'
    },
    'partyAAdmin':{
        text:'Admin',
        color:'light-blue'
    },
    'partyAEm':{
        text:'Nhân viên',
        color:'deep-purple darken-1'
    },
    'partyB':{
        text:'Đối tác',
        color:'orange darken-3'
    }
}
const PartyStatusMap={
    1:{
        text:'Mới tạo',
        color:'blue-grey lighten-1'
    },
    2:{
        text:'Đã kích hoạt',
        color:'green'
    },
    0:{
        text:'Hủy',
        color:'red'
    },
    
}
const SImageStatusMap={
    1:{
        text:'Đang xử lý',
        color:'blue-grey lighten-1'
    },
    2:{
        text:'Hợp lệ',
        color:'green'
    },
    0:{
        text:'Không hợp lệ',
        color:'red'
    },
    
}
const TaskStatusMap={
    '-3':{
        text:'Hủy',
        color:'orange'
    },
    '-2':{
        text:'Đang ẩn',
        color:'brown lighten-2'
    },
    '-1':{
        text:'Nhiệm vụ gốc',
        color:'purple darken-4'
    },
    1:{
        text:'Mới tạo',
        color:'blue-grey lighten-1'
    },

    2:{
        text:'Đã báo cáo',
        color:'blue'
    },
    3:{
        text:'Đã duyệt',
        color:'green'
    },
    0:{
        text:'Không duyệt',
        color:'red'
    },
    
}
const PlaceRentalStatusMap={
    1:{
        text:'Đang yêu cầu',
        color:'blue-grey lighten-1'
    },
    2:{
        text:'Đang thuê',
        color:'green'
    },
    3:{
        text:'Hết hạn',
        color:'orange darken-2'
    },
    0:{
        text:'Hủy',
        color:'red'
    },
    '-1':{
        text:'Yêu cầu hủy',
        color:'red'
    }
    
}
const TimeUnit={
    'day':{
        text:'Ngày',
        color:'blue-grey lighten-1'
    },
    'week':{
        text:'Tuần',
        color:'green'
    },
    'moth':{
        text:'Tháng',
        color:'orange darken-2'
    },
    'year':{
        text:'Năm',
        color:'red'
    },
    
}
const SmallTimeUnit={
    'minute':{
        text:'Phút',
        color:'blue-grey lighten-1'
    },
    'hour':{
        text:'Giờ',
        color:'blue-grey lighten-1'
    },
    'day':{
        text:'Ngày',
        color:'blue-grey lighten-1'
    },
    'week':{
        text:'Tuần',
        color:'green'
    },
    'month':{
        text:'Tháng',
        color:'orange darken-2'
    },
    
    
}
const TaskType={
    'setup':{
        text:'Lắp đặt',
        color:'blue-grey lighten-1',
        icon:'fas fa-cogs'
    },
    'check':{
        text:'Kiểm tra',
        color:'green',
        icon:'fas fa-flag-checkered'
    },
    'fee':{
        text:'Chi trả',
        color:'light-blue darken-1',
        icon:'far fa-money-bill-alt'
    },
    'report':{
        text:'Báo cáo tình trạng',
        color:'orange darken-2',
        icon:'fas fa-camera-retro'
    }, 
}
// const TaskStatus={
//     1:{
//         text:'Mới tạo',
//         color:'blue-grey lighten-1'
//     },
//     2:{
//         text:'Đã kích hoạt',
//         color:'green'
//     },
//     0:{
//         text:'Hủy',
//         color:'red'
//     }
// }
const RepeatType={
    'repeat':{
        text:'Lặp lại',
        color:'blue-grey lighten-1',
        icon:'fas fa-calendar-alt'
    },
    'once':{
        text:'Một lần',
        color:'green',
        icon:'fas fa-dice-one'
    },
}
const ColorLst=[
    {
        name:'red',
        code:'#F44336'
    },
    {
        name:'teal',
        code:'#009688'
    },
    {
        name:'pink',
        code:'#E91E63'
    },
    {
        name:'purple',
        code:'#9C27B0'
    },
    {
        name:'blue',
        code:'#2196F3'
    },
    {
        name:'deep-purple',
        code:'#673AB7'
    },
    
    {
        name:'cyan darken-4',
        code:'#006064'
    },
    {
        name:'lime',
        code:'#CDDC39'
    },
    {
        name:'brown ',
        code:'#795548'
    },

]

module.exports={
    RoleMap:RoleMap,
    PartyStatusMap:PartyStatusMap,
    PlaceRentalStatusMap:PlaceRentalStatusMap,
    TimeUnit:TimeUnit,
    TaskType:TaskType,
    RepeatType:RepeatType,
    SmallTimeUnit:SmallTimeUnit,
    TaskStatus:TaskStatusMap,
    ColorLst:ColorLst,
    SImageStatus:SImageStatusMap
}
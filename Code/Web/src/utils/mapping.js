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

module.exports={
    RoleMap:RoleMap,
    PartyStatusMap:PartyStatusMap
}
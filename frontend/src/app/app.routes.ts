    import { Routes } from '@angular/router';
    import { PlayerComponent} from './Player/player-component/player-component';
    import {StaffComponent} from './Staff/staff-component/staff-component';
    import { ClubComponent } from './Club/club-component/club-component';
    import { PlayersComponent} from './Player/players-component/players-component';
    import {PlayerAddComponent} from './Player/player-add-component/player-add-component';
    import { UserComponent } from './User/user-component/user-component';
    import {AdminstrationComponent} from './Adminstration/adminstration-component/adminstration-component'
    import {ListAdminstrationComponent} from './Adminstration/list-adminstration-component/list-adminstration-component'
    import{AddAdminstrationComponent} from './Adminstration/add-adminstration-component/add-adminstration-component'
    import {ListStaffComponent} from './Staff/list-staff-component/list-staff-component'
    import {AddStaffComponent} from './Staff/add-staff-component/add-staff-component'
    import { UsersComponent} from './User/users-component/users-component'
    import { ListClubsComponent } from './Club/list-clubs-component/list-clubs-component';
    import {AddClubComponent} from './Club/add-club-component/add-club-component'

    export const routes: Routes = [
    
        { path:'' , component:ClubComponent},
        {path: "user", component :UserComponent },
        {path: "listusers", component :UsersComponent },
        {path: "admin", component :AdminstrationComponent },
        {path: "addadmin", component :AddAdminstrationComponent },
        {path:'admins' , component:ListAdminstrationComponent},
        { path:'player' , component:PlayerComponent},
        {path:'addplayer' , component:PlayerAddComponent},
        {path:'staff' , component:StaffComponent},
        {path:'liststaff' , component:ListStaffComponent},
        {path:'addstaff' , component:AddStaffComponent},
        {path:'club' , component:ClubComponent},
        {path:'players' , component:PlayersComponent},
        {path:'clubs' , component:ListClubsComponent},
        {path:'addclub' , component:AddClubComponent},
        {path:'**',component:ClubComponent },
        
    ];  
    
    import { Routes } from '@angular/router';
    import { PlayerComponent} from './Player/player-component/player-component';
    import { ClubComponent } from './Club/club-component/club-component';
    import {PlayerAddComponent} from './Player/player-add-component/player-add-component';
    import { UserComponent } from './User/user-component/user-component';
    import {ListAdminstrationComponent} from './Adminstration/list-adminstration-component/list-adminstration-component'
    import{AddAdminstrationComponent} from './Adminstration/add-adminstration-component/add-adminstration-component'
    import {ListStaffComponent} from './Staff/list-staff-component/list-staff-component'
    import {AddStaffComponent} from './Staff/add-staff-component/add-staff-component'
    import { UsersComponent} from './User/users-component/users-component'
    import { ListClubsComponent } from './Club/list-clubs-component/list-clubs-component';
    import {AddClubComponent} from './Club/add-club-component/add-club-component'
    import { AddCategoryComponent} from'./categories/add-category-component/add-category-component';

    export const routes: Routes = [
    
        { path:'' , component:ClubComponent},
        {path: "user", component :UserComponent },
        {path: "listusers", component :UsersComponent },
        {path: "addadmin", component :AddAdminstrationComponent },
        {path:'admins' , component:ListAdminstrationComponent},
        { path:'player' , component:PlayerComponent},
        {path:'addplayer' , component:PlayerAddComponent},
        {path:'liststaff' , component:ListStaffComponent},
        {path:'addstaff' , component:AddStaffComponent},
        {path:'club/:id' , component:ClubComponent},
        {path:'clubs' , component:ListClubsComponent},
        {path:'addclub' , component:AddClubComponent},
        {path:'addcat' , component:AddCategoryComponent},

        {path:'**',component:ClubComponent },
        
    ];  
    
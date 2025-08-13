    import { Routes } from '@angular/router';
    import { PlayerComponent} from './Player/player-component/player-component';
    import { ClubComponent } from './Club/club-component/club-component';
    import { UserComponent } from './User/user-component/user-component';
    import {ListAdminstrationComponent} from './Adminstration/list-adminstration-component/list-adminstration-component'
    import {ListStaffComponent} from './Staff/list-staff-component/list-staff-component'
    import { UsersComponent} from './User/users-component/users-component'
    import { ListClubsComponent } from './Club/list-clubs-component/list-clubs-component';
    import {AddClubComponent} from './Club/add-club-component/add-club-component'
    import { AddCategoryComponent} from'./categories/add-category-component/add-category-component';
    import{AddUserComponent} from './User/add-user-component/add-user-component'
    import { AddMemberComponent } from './ClubMember/add-member-component/add-member-component';
    import{ClubCategories} from './Club/club-categories/club-categories/club-categories';
    import { ClubMemberComponent } from './ClubMember/club-member-component/club-member-component';
    import { ListPlayersComponent } from './Player/list-players-component/list-players-component';
    import { AddPlayerComponent } from './Player/add-player-component/add-player-component';
    import { AddStaffComponent } from './Staff/add-staff-component/add-staff-component';
    import { StaffComponent } from './Staff/staff-component/staff-component';
    import { AdministrationComponent } from './Adminstration/adminstration-component/administration-component';
    import { AddAdministrationComponent } from './Adminstration/add-adminstration-component/add-administration-component';
import { CategroyListComponent } from './categories/categroy-list-component/categroy-list-component';


    export const routes: Routes = [
    
        
        {path: "user", component :UserComponent },
        
        {path: "addplayer", component :AddPlayerComponent },
        {path: "addstaff", component :AddStaffComponent },
        {path: "addadmin", component :AddAdministrationComponent },


        //list
        {path:'club/admins' , component:ListAdminstrationComponent},
        {path:'club/staff' , component:ListStaffComponent},
        {path:'club/players' , component:ListPlayersComponent},

        //profiles
        {path:'club/players/profile' , component:PlayerComponent},
        {path:'club/staff/profile' , component:StaffComponent},
        {path:'club/admins/profile' , component:AdministrationComponent},
       


        {path:'club' , component:ClubComponent},
        {path:'club/categories' , component:ClubCategories},


        //admin
        { path:'admin/clubs' , component:ListClubsComponent},
        {path:'admin/club/profile' , component:ClubComponent},
        {path:'admin/clubs/addclub' , component:AddClubComponent},
        {path:'admin/categories/addcategory' , component:AddCategoryComponent},
        
        {path:'admin/categories' , component:CategroyListComponent},
        {path: "admin/adduser", component :AddUserComponent },
        {path: "admin/listusers", component :UsersComponent },

        {path:'**',component:ListClubsComponent },

        
    ];  
    
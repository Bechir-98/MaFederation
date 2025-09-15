    import { Routes } from '@angular/router';
    import { PlayerComponent} from './Player/player-component/player-component';
    import { ClubComponent } from './Club/club-component/club-component';
    import {ListAdministrationComponent} from './Adminstration/list-adminstration-component/list-adminstration-component'
    import {ListStaffComponent} from './Staff/list-staff-component/list-staff-component'
    import { ListClubsComponent } from './Club/list-clubs-component/list-clubs-component';
    import {AddClubComponent} from './Club/add-club-component/add-club-component'
    import { AddCategoryComponent} from'./categories/add-category-component/add-category-component';
    import{ClubCategories} from './Club/club-categories/club-categories/club-categories';
    import { ListPlayersComponent } from './Player/list-players-component/list-players-component';
    import { AddPlayerComponent } from './Player/add-player-component/add-player-component';
    import { AddStaffComponent } from './Staff/add-staff-component/add-staff-component';
    import { StaffComponent } from './Staff/staff-component/staff-component';
    import { AdministrationComponent } from './Adminstration/adminstration-component/administration-component';
    import { AddAdministrationComponent } from './Adminstration/add-adminstration-component/add-administration-component';
    import { CategroyListComponent } from './categories/categroy-list-component/categroy-list-component';
    import { RolePermissionComponent } from './Role/role-permission.component/role-permission.component';
    import { AddModComponent } from './Dashboard/add-mod-component/add-mod.component';
    import { ModsComponent } from './Dashboard/mods-component/mods.component';
    import { ModComponent } from './Dashboard/mod-component/mod.component';
import { UserVerificationsComponent } from './Dashboard/user-verification/user-verification.component';
import { ClubVerificationsComponent } from './Dashboard/club-verification/club-verifications.component/club-verifications.component';
    import {LoginComponent} from './User/login.component/login.component';
    import {AdminTypeComponent} from './Dashboard/admin-type.component/admin-type.component';


    export const routes: Routes = [

      {path: "login", component :LoginComponent },


        {path: "addplayer", component :AddPlayerComponent },
        {path: "addstaff", component :AddStaffComponent },
        {path: "addadmin", component :AddAdministrationComponent },


        //list
        {path:'club/admins' , component:ListAdministrationComponent},
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
        {path:'admin/roles' , component:RolePermissionComponent},
        {path:'admin/user-verifications' , component:UserVerificationsComponent},
        {path:'admin/user-verifications' , component:UserVerificationsComponent},
        {path:'admin/club-requests', component:ClubVerificationsComponent},


        {path:'admin/categories' , component:CategroyListComponent},
        {path: "admin/addmod", component :AddModComponent},
        {path: "admin/list", component :AdminTypeComponent},
        {path: "admin/profile", component :ModComponent},

        {path:'**',component:LoginComponent },


    ];

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

    export const routes: Routes = [
    
        { path:'' , component:ListClubsComponent},
        {path: "user", component :UserComponent },
        {path: "adduser", component :AddUserComponent },
        {path: "listusers", component :UsersComponent },
        
        {path: "addmember", component :AddMemberComponent },
        {path:'clubs/:clubId/members/:memberId',component:ClubMemberComponent},

        {path:'admins' , component:ListAdminstrationComponent},
        
        {path:'liststaff' , component:ListStaffComponent},
        {path:'clubs/:id' , component:ClubComponent},
        {path:'clubs' , component:ListClubsComponent},
        {path:'clubs/:id/categories',component:ClubCategories},
        {path:'addclub' , component:AddClubComponent},
        {path:'addcat' , component:AddCategoryComponent},
        {path:'**',component:ListClubsComponent },
        
    ];  
    
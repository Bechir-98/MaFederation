    import { Routes } from '@angular/router';
    import { PlayerComponent} from './Player/player-component/player-component';
    import {StaffComponent} from './Staff/staff-component/staff-component';
    import { ClubComponent } from './Club/club-component/club-component';
    import { PlayersComponent} from './Player/players-component/players-component';
    import {PlayerAddComponent} from './Player/player-add-component/player-add-component';
    import { UserComponent } from './User/user-component/user-component';



    export const routes: Routes = [
    
        { path:'' , component:ClubComponent},
        {path: "user", component :UserComponent },
        { path:'player' , component:PlayerComponent},
        { path:'add' , component:PlayerAddComponent},
        {path:'staff' , component:StaffComponent},
        {path:'club' , component:ClubComponent},
        {path:'players' , component:PlayersComponent},
        {path:'**',component:ClubComponent },
    ];  
    
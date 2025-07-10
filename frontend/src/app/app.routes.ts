import { Routes } from '@angular/router';
import { PlayerComponent} from './Player/player-component/player-component';
import {StaffComponent} from './Staff/staff-component/staff-component';
import { ClubComponent } from './Club/club-component/club-component';
import { PlayersComponent} from './Player/players-component/players-component';


export const routes: Routes = [

    { path:'' , component:ClubComponent},
    { path:'player' , component:PlayerComponent},
    {path:'staff' , component:StaffComponent},
    {path:'club' , component:ClubComponent},
    {path:'players' , component:PlayersComponent}
];  

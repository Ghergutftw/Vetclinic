import { Injectable } from '@angular/core';
import {AuthentificationService} from "../authentification.service";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class RouterGuardService implements CanActivate {

  constructor(public authService: AuthentificationService,
              public router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
      if(this.authService.isUserLoggedIn()){
        return true;
      }else {
        this.router.navigate(['login'])
        return false;
      }
    }


}

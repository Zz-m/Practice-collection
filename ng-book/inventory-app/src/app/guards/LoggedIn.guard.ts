import {CanActivate} from "@angular/router";
import {Injectable} from "@angular/core";
import {AuthService} from "../service/auth.service";

@Injectable()
export class LoggedInGuard implements CanActivate {


    constructor(private authService: AuthService) {
    }

    canActivate(): boolean {
        return this.authService.isLoggedIn();
    }

}
export var LOGGED_IN_GUARD_PROVIDERS: Array<any> = [
    {provide: LoggedInGuard, useClass: LoggedInGuard}
]

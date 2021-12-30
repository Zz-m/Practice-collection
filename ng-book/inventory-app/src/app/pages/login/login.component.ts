import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../service/auth.service";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

    message: string = ''

    constructor(public authService: AuthService) {
    }

    ngOnInit(): void {
    }

    login(username: string, password: string): boolean {
        this.message = ''
        if (!this.authService.login(username, password)) {
            this.message = "账号密码错误"
            setTimeout(function () {
                LoginComponent.prototype.message = ''
            }.bind(this), 2500);
        }
        return false;
    }

    logout(): boolean {
        this.authService.logout()
        return false;
    }
}

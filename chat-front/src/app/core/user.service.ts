import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { getHost } from "./host-resolve";

@Injectable({
    providedIn: 'root',
})
export class UserService {
    private http = inject(HttpClient);

    private url = getHost();

    save(obj: any) {
        return this.http.post(`${this.url}user`, obj);
    }

    listar() {
        return this.http.get<any>(`${this.url}user`);
    }

    login() {
        return this.http.get(`${this.url}user/login`);
    }

    delete(id: string) {
        return this.http.delete(`${this.url}user/${id}`);
    }

    getPorId(id: string) {
        return this.http.get(`${this.url}user/getid/${id}`);
    }


}

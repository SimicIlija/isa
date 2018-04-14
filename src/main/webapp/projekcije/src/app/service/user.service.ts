import { Injectable } from '@angular/core';
import {HttpHeaders, HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Register } from '../model/Register';
import * as express from 'express';
import compression from 'compression';
import {enableProdMode} from '@angular/core';


enableProdMode();
const server = express();
server.use(compression());

server.set('view engine', 'html');
server.set('views', 'public');
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class UserService {

  private userURL = 'localhost:1234/user';
  constructor(private http:HttpClient) { }

  registerUser(register:Register):Observable<Register>{
    const url = `${this.userURL}/registerUser`
    return this.http.post<Register>(url,register,httpOptions);
  }

  


}

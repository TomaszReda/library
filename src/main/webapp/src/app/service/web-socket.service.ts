import {Injectable} from '@angular/core';
import * as Stomp from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import {environment} from "../../environments/environment.prod";

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  private url = environment.url;

  private stompClient = null;

  constructor() {
  }

  connect() {
    const socket = new SockJS(this.url + 'websockets');

    this.stompClient = Stomp.over(socket);

    const _this = this;
    this.stompClient.connect({}, function (frame) {
      console.log('Connected: ' + frame);
      _this.stompClient.subscribe('/app/notification', function (hello) {
      });
    });

  }

  disconnect() {
    if (this.stompClient != null) {
      this.stompClient.disconnect();
    }
    console.log('Disconnected!');
  }

  sendNotification() {
    this.stompClient.send(
      '/notification',
      {},
      JSON.stringify({'name': "test"})
    );
  }


}

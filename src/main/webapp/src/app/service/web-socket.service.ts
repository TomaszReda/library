import {Injectable} from '@angular/core';
import * as Stomp from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {


  private stompClient = null;

  constructor() {
  }

  connect() {
    const socket = new SockJS('http://localhost:8080/websockets');

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

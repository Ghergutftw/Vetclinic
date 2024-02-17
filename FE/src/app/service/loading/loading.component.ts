// loading.component.ts

import { Component, OnInit } from '@angular/core';
import { LoadingService } from './loading.service';

@Component({
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.css']
})
export class LoadingComponent implements OnInit {
  isLoading: boolean = false;

  constructor(private loadingService: LoadingService) { }

  ngOnInit(): void {
    this.loadingService.isLoading.subscribe((value: boolean) => {
      this.isLoading = value;
    });
  }
}

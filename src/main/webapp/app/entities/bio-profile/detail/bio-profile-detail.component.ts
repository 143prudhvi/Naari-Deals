import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBioProfile } from '../bio-profile.model';

@Component({
  selector: 'jhi-bio-profile-detail',
  templateUrl: './bio-profile-detail.component.html',
})
export class BioProfileDetailComponent implements OnInit {
  bioProfile: IBioProfile | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bioProfile }) => {
      this.bioProfile = bioProfile;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

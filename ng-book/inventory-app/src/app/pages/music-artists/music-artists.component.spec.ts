import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MusicArtistsComponent } from './music-artists.component';

describe('MusicArtistsComponent', () => {
  let component: MusicArtistsComponent;
  let fixture: ComponentFixture<MusicArtistsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MusicArtistsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MusicArtistsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MusicTracksComponent } from './music-tracks.component';

describe('MusicTracksComponent', () => {
  let component: MusicTracksComponent;
  let fixture: ComponentFixture<MusicTracksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MusicTracksComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MusicTracksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

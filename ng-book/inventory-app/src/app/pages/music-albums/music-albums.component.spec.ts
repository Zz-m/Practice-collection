import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MusicAlbumsComponent } from './music-albums.component';

describe('MusicAlbumsComponent', () => {
  let component: MusicAlbumsComponent;
  let fixture: ComponentFixture<MusicAlbumsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MusicAlbumsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MusicAlbumsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

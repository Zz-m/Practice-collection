import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabAppComponent } from './tab-app.component';

describe('TabAppComponent', () => {
  let component: TabAppComponent;
  let fixture: ComponentFixture<TabAppComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TabAppComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TabAppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

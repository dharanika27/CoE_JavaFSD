import { TestBed } from '@angular/core/testing';

import { IplTeamsService } from './ipl-teams.service';

describe('IplTeamsService', () => {
  let service: IplTeamsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IplTeamsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

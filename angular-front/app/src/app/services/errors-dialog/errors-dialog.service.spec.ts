import { TestBed } from '@angular/core/testing';

import { ErrorsDialogService } from './errors-dialog.service';

describe('ErrorsDialogService', () => {
  let service: ErrorsDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ErrorsDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

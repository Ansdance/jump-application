import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IStatisticHistory } from '../statistic-history.model';
import { StatisticHistoryService } from '../service/statistic-history.service';

@Component({
  standalone: true,
  templateUrl: './statistic-history-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class StatisticHistoryDeleteDialogComponent {
  statisticHistory?: IStatisticHistory;

  protected statisticHistoryService = inject(StatisticHistoryService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.statisticHistoryService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

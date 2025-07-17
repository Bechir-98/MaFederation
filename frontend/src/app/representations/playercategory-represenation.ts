export interface PlayerCategoryRepresentation {
  playerId: number;    // FK to Player (userId)
  categoryId: number;  // FK to Category
  name: string;        // Name of the category
}

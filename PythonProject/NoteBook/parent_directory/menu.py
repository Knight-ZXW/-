import sys

from parent_directory.notebook import NoteBook,Note


class Menu:
    def __init__(self):
        self.notebook = NoteBook()
        self.choices = {
            "1":self.show_notes,
            "2":self.search_notes,
            "3":self.add_notes,
            "4":self.modify_note,
            "5":self.quit,
        }

    def display_menu(self):
        print("""
        1. Show all notes
        2. Search Notes
        3. Add Notes
        4. modify_notes
        5. self.quit
        """)

    def run(self):
        while True:
            self.display_menu()
            choice = input("Enter an options:")
            action = self.choices.get(choice)
            if action:
                action()
            else:
                print("{0} is no a valid choice".format(choice))

    def show_notes(self,notes = None):
        if not notes:
            notes = self.notebook.notes
        for note in notes:
            print("{0}:{1}\n{2}".format(note.id,note.tag,note.memo))

    def search_notes(self):
        filter = input("Search for: ")
        notes = self.notebook.search(filter)
        self.show_notes(notes)

    def add_notes(self):
        memo = input("Enter a memo")
        self.notebook.new_note(memo)
        print("your note has been add")

    def modify_note(self):
        id = input("Enter a note id")
        memo= input("Enter a memo")
        tags = input("Enter tags")
        if memo:
            self.notebook.modify_momo(id,memo)
        if tags:
            self.notebook.modify_momo(id,tags)

    def quit(self):
        print("Thanks for using notebook today!")
        sys.exit(0)

if __name__ == "__main__":
    Menu().run()
import datetime
# 为所有新的备注存储下一个可用的id
last_id =0


class Note:
    '''Represent anote in the notebook. Match against a string in searches and sore tags foreach note'''
    def __init__(self,memo,tag=''):
        self.memo = memo
        self.tag = tag
        self.creation_date = datetime.date.today()
        global  last_id
        last_id += 1
        self.id = last_id

    def match(self,filter):
        return filter in self.memo or filter in self.tag


class NoteBook:
    def __init__(self):
        self.notes = []

    def new_note(self,memo,tags=''):
        self.notes.append(Note(memo,tags))

    def modify_momo(self,note_id,memo):
        for note in self.notes:
            if note.id == note_id:
                note.memo = memo
                break

    def search(self,filter):
        return [note for note in self.notes if note.match(filter)]
